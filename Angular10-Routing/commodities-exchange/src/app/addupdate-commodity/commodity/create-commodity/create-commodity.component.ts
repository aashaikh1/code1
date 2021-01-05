import { Component, OnInit, EventEmitter, Output  } from '@angular/core';

import { Commodity } from '../../../commodity-core/model/commodity';

import { FormControl, FormGroup, Validators, FormBuilder } from '@angular/forms';  

import { CommodityService } from '../../../commodity-core/services/commodity.service';

@Component({
  selector: 'app-create-commodity',
  templateUrl: './create-commodity.component.html',
  styleUrls: ['./create-commodity.component.css']
})
export class CreateCommodityComponent implements OnInit{
  
  public commodity: Commodity;
  public markets = ['CBOT', 'ABX', 'PMEX'];
  public message: string = "Symbol should be unique to create new Commodity.";
  public commodityForm: FormGroup;
  constructor(private commodityService: CommodityService, private fb: FormBuilder) {
	this.buildForm();  
    this.commodity =  new Commodity('', '', 0, 0, 'ABX');
  }
  
  
  buildForm() {
    this.commodityForm = this.fb.group({
      name: [null, [Validators.required, Validators.minLength(2), Validators.maxLength(15)]],
      symbol: [null, [Validators.required, Validators.minLength(2), Validators.maxLength(3)]],
      currentValue: [0, [Validators.required, Validators.min(0), Validators.max(9999)]],
	  market: [null, Validators.required]
    });
  }


  resetForm() {
    this.commodityForm.reset();
  }

  onSubmit() {
	if (this.commodityForm.valid) {
		this.commodity = Object.assign({}, this.commodityForm.value);
		this.commodity.symbol = this.commodity.symbol.toUpperCase();
		this.createCommodity(this.commodity)
	}
  }
  
  
	ngOnInit() {  
	}
  
	createCommodity(commodity: Commodity) {
		this.commodityService.saveCommodity(commodity).subscribe(
			(data) => {
				this.message = data.msg;
			},
				error => {
				this.message = error;
			});
	}
	
}




