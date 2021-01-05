import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-search-commodity',
  templateUrl: './search-commodity.component.html',
  styleUrls: ['./search-commodity.component.css']
})
export class SearchCommodityComponent implements OnInit {
  public commodity: Object;
  constructor() { }

  ngOnInit(): void {
  }
	invokePlotCommodity(commodity1: Object){
		this.commodity = commodity1; 
		console.log('Commodity ', this.commodity, ' event received by SearchCommodityComponent.invokePlotCommodity.');
	}
}
