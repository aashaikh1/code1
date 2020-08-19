import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { ReactiveFormsModule }   from '@angular/forms';
import { AppComponent } from './app.component';
import { CommodityObjectComponent } from './commodity/commodity-object/commodity-object.component';
import { CreateCommodityComponent } from './commodity/create-commodity/create-commodity.component';
import { CommodityListComponent } from './commodity/commodity-list/commodity-list.component';
import { CommodityService } from './services/commodity.service';
import { HttpClientModule } from '@angular/common/http';

@NgModule({
  declarations: [
    AppComponent,
    CommodityObjectComponent,
    CreateCommodityComponent,
    CommodityListComponent
  ],
  imports: [
    BrowserModule,
	ReactiveFormsModule,
	HttpClientModule 
  ],
  providers: [
	CommodityService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
