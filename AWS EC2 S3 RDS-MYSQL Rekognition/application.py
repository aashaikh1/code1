"AWS Image Recognition"
from __future__ import print_function
import sys

import context
from flask import Flask, render_template_string
from flask_wtf import FlaskForm
from flask_wtf.file import FileRequired, FileField
import requests
import boto3
import mysql.connector
from PIL import Image, ExifTags
import os
from io import BytesIO

application = Flask(__name__)
application.secret_key = context.PYTHONFLASK_SECRET

EXIF_ORIENTATION = 274

def hex_random(n_bytes):
    return os.urandom(n_bytes).hex()

def resize_photo(_photo, size):

    try:
        image = Image.open(_photo)
    except IOError:
        print("Error while opening the photo image during rezise")
        return None

    try:
        exif = dict(image._getexif().items())
        if exif[EXIF_ORIENTATION] == 3:
            image = image.rotate(180, expand=True)
        elif exif[EXIF_ORIENTATION] == 6:
            image = image.rotate(270, expand=True)
        elif exif[EXIF_ORIENTATION] == 8:
            image = image.rotate(90, expand=True)
    except:
        print("No exif data")
        
    target_ratio = size[0] / float(size[1])
    source_ratio = image.size[0] / float(image.size[1])

    if image.size < size:
        target_width, target_height = image.size
    elif target_ratio > source_ratio:
        target_width = int(image.size[0] * size[1]/float(image.size[1]))
        target_height = size[1]
    else:
        target_width = size[0]
        target_height = int(image.size[1] * size[0]/float(image.size[0]))
    image = image.resize((target_width, target_height), resample=Image.LANCZOS)

    resized_image = Image.new("RGBA", size)
    top_left = (int((size[0]-target_width) / float(2)),
               int((size[1]-target_height) / float(2)))
    resized_image.paste(image, top_left)
    bytes_stream = BytesIO()
    resized_image.save(bytes_stream, 'PNG')
    return bytes_stream.getvalue()


def get_all_photos_with_null_user():
    db_connection = get_db_connection()
    cursor = db_connection.cursor(dictionary=True)
    cursor.execute("""SELECT object_key, labels, created_datetime FROM photo WHERE cognito_username is null order by created_datetime desc""")
    resultset = cursor.fetchall()
    cursor.close()
    db_connection.close()
    return resultset

def add_new_photo(object_key, labels):
    db_connection = get_db_connection()
    cursor = db_connection.cursor(dictionary=True) 
    cursor.execute("INSERT INTO photo (object_key, labels) VALUES (%s, %s);", (object_key, labels))
    db_connection.commit()
    cursor.close()
    db_connection.close()

def get_db_connection():
    db_connection = mysql.connector.connect(user=context.DB_USERNAME, password=context.DB_PASSWORD, host=context.DB_HOSTNAME, database=context.DB_NAME, use_pure=True)
    return db_connection



@application.route("/", methods=('GET', 'POST'))
def base():

    s3_client = boto3.client('s3')
    all_new_labels = ["No labels"]    

    form = PhotoFlaskForm()
    photo_url = None
    if form.validate_on_submit():
        photo_bytes = resize_photo(form.photo.data, (300, 300))
        if photo_bytes:
            photoId = "photos/" + hex_random(8) + '.png'
            s3_client.put_object(Bucket=context.PHOTOS_BUCKET_NAME, Key=photoId, Body=photo_bytes, ContentType='image/png')
            photo_url = s3_client.generate_presigned_url('get_object', Params={'Bucket': context.PHOTOS_BUCKET_NAME, 'Key': photoId})

            rekognition_client = boto3.client('rekognition')
            rekognition_response = rekognition_client.detect_labels(Image={'S3Object': {'Bucket': context.PHOTOS_BUCKET_NAME, 'Name': photoId}})
            all_new_labels = [photo_label['Name'] for photo_label in rekognition_response['Labels']]

            all_new_labels_comma_delimited = ", ".join(all_new_labels)
            add_new_photo(photoId, all_new_labels_comma_delimited)


    photos = get_all_photos_with_null_user()
    for photo in photos:
        photo["signed_url"] = s3_client.generate_presigned_url('get_object', Params={'Bucket': context.PHOTOS_BUCKET_NAME, 'Key': photo["object_key"]})
        
    #print('photos size', file=sys.stderr)
    #print(str(len(photos)), file=sys.stderr)

    return render_template_string("""
            {% extends "base.html" %}
            {% block content %}
            <form method="POST" enctype="multipart/form-data" action="{{ url_for('base') }}">
                {{ form.csrf_token }}
                <div>
                    <label>Photo</label>
                    {{ form.photo() }}
                </div>
                </br>
                <div>
                    <div>
                        <input type="submit" value="Upload">
                    </div>
                </div>
            </form>
            
            {% if photos %}
                <hr/>
                <h4>Photos</h4>
                {% for photo in photos %}
                    <table>
                    <tr> <td rowspan="4"><img width="150" src="{{photo.signed_url}}" /> </td></tr>
                    <tr> <th scope="row" style="text-align:left">Photo Details by AWS AI</th> <td>{{photo.labels}}</td> </tr>
                    <tr> <th scope="row" style="text-align:left">Upload Date</th> <td>{{photo.created_datetime}} UTC</td> </tr>
                    </table>
                {% endfor %}
            {% endif %}

            {% endblock %}
                """, form=form, url=photo_url, photos=photos, all_labels=all_new_labels)

class PhotoFlaskForm(FlaskForm):
    photo = FileField('image', validators=[FileRequired()])
    
if __name__ == "__main__":
    use_c9_debugger = False
    application.run(host='0.0.0.0', port=8080)

