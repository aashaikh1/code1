import { Component, OnInit } from '@angular/core';
import { Commodity } from '../../../commodity-core/model/commodity';

import { Observable } from 'rxjs/Observable';

import { CommodityService } from '../../../commodity-core/services/commodity.service';

@Component({
  selector: 'app-commodity-list',
  templateUrl: './commodity-list.component.html',
  styleUrls: ['./commodity-list.component.css']
})
export class CommodityListComponent implements OnInit {
  public commodities$: Observable<Commodity[]>; 
  constructor(private commodityService: CommodityService) { }
  
	ngOnInit() {
		this.commodityService.getCommodities();//load the list at startup
		this.commodityService.commodities$.subscribe(currentCommodities => {this.refreshListDelayed()});
	}  
	
	
  refreshListDelayed() {
	setTimeout(() => {
		console.log('sleep');
		this.refreshList(null);
	}, 500);
  }	
	
	
  refreshList(event) {
	this.commodities$ = this.commodityService.getCommodities();
  }	
	
  //CommodityListComponent template is parent/enclosing CommodityObjectComponent(child emitting to parent here)
  togglePreferredInList(commodity: Commodity) {
    console.log('Preferred ', commodity, ' triggered');
	this.commodityService.togglePreferredInService(commodity).subscribe(
				(data) => {
					//this.refreshList(null);
				},
				error => {
					console.log(error);
				});
  }


  deleteInList(commodity: Commodity) {
    console.log('Delete ', commodity, ' triggered');
	this.commodityService.deleteCommodityInService(commodity).subscribe(
				(data) => {
					//this.refreshList(null);
				},
				error => {
					console.log(error);
				});
  }

}



