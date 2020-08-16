import { Component, OnInit, EventEmitter, Output  } from '@angular/core';
import { Commodity } from '../../model/commodity';
import { CommodityService } from '../../services/commodity.service';
import { NgForm } from '@angular/forms';  

@Component({
  selector: 'app-create-commodity',
  templateUrl: './create-commodity.component.html',
  styleUrls: ['./create-commodity.component.css']
})
export class CreateCommodityComponent implements OnInit{
  
  public commodity: Commodity;
  public markets = ['CBOT', 'ABX', 'PMEX'];
  public message: string = "Symbol should be unique to create new Commodity.";
  constructor(private commodityService: CommodityService) {
    this.commodity =  new Commodity('', '', 0, 0, 'ABX');
  }

  
	ngOnInit() {  
	}
  
	createCommodity(commodityForm:NgForm) {
		if (commodityForm.valid) {
			
			this.commodityService.saveCommodity(commodityForm.value).subscribe(
				(data) => {
					this.message = data.msg;
				},
					error => {
					this.message = error;
				});
			
		}
	}

}

