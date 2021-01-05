import { Component, Input, OnChanges } from '@angular/core';

import { Commodity } from '../../../commodity-core/model/commodity';

import { CommodityHistory } from '../../../commodity-core/model/commodity-history';

import { CommodityService } from '../../../commodity-core/services/commodity.service';
import { Observable } from 'rxjs/Observable';

@Component({
    selector: 'plotly-draw',
    template: '<plotly-plot [data]="graphDetails.data" [layout]="graphDetails.layout"></plotly-plot>',
	//template: '{{commodity.name}}'
})
export class CommodityPlotComponent implements OnChanges{
	@Input() public commodity: Commodity;
	
	public graphDetails = {
        data: [
            { x: [new Date(), new Date(), new Date()], y: [2, 6, 3], type: 'scatter', mode: 'lines+points', marker: {color: 'red'} },
        ],
        layout: {width: 420, height: 240, plot_bgcolor: 'rgb(186, 193, 204)', title: ' '}
    };

	private xval: number[] = [5, 2, 3];
	private yval: number[] = [7, 1, 9];
	
	ngOnChanges() {
		console.log('Commodity ', this.commodity, ' received..msg from CommodityPlotComponent.onChanges.');
	  
		if(this.commodity){
			this.commodityService.getCommoditiesHistory(this.commodity.symbol).subscribe(
					(data) => {
					  console.log('getCommoditiesHistory response: ' + data.symbol + ' ' + data.x + ' ' + data.y);	
					  this.graphDetails = {
						data: [
							{ x: data.x, y: data.y, type: 'scatter', mode: 'lines+points',  marker: {color: 'red'} },
						],
						layout: {width: 420, height: 240, plot_bgcolor: 'rgb(186, 193, 204)', title: this.getTitle(data.y)}
					  };	  
					},
					error => {
						console.log(error);
					});	  
		}else{
		this.setDefaultGraph();
		}
	}	
	
	
	getTitle(axisData: number[]){
		if(axisData.length < 2){
			return this.commodity.name + ' (New Commodity, No previous prices yet)';
		}
		return this.commodity.name;
	}

	setDefaultGraph(){
		this.graphDetails = {
			data: [
				{ x: [new Date(), new Date(), new Date()], y: [2, 6, 3], type: 'scatter', mode: 'lines+points', marker: {color: 'red'} },
			],
			layout: {width: 420, height: 240, plot_bgcolor: 'rgb(186, 193, 204)', title: ' '}
		};
	}
	

	constructor(private commodityService: CommodityService) { 
		console.log('Commodity ', this.commodity, ' received by CommodityPlotComponent.constructor.');
	}

}