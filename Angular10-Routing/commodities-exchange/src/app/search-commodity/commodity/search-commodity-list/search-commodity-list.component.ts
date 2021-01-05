import { Component, OnInit, EventEmitter, Output } from '@angular/core';

import { Commodity } from '../../../commodity-core/model/commodity';

import { Observable } from 'rxjs/Observable';

import { CommodityService } from '../../../commodity-core/services/commodity.service';

import { debounceTime, switchMap, distinctUntilChanged, startWith, share } from 'rxjs/operators';

import { Subject } from 'rxjs/Subject';

@Component({
  selector: 'app-search-commodity-list',
  templateUrl: './search-commodity-list.component.html',
  styleUrls: ['./search-commodity-list.component.css']
})
export class SearchCommodityListComponent implements OnInit {
  public commodities$: Observable<Commodity[]>; 
  public searchQuery: string = '';
  private selectedCommodity: Commodity;
  
  @Output() onCommoditySelectedEmitter: EventEmitter<Commodity>;
  
  constructor(private commodityService: CommodityService) { 
	this.onCommoditySelectedEmitter = new EventEmitter<Commodity>();
  }

	commodityClicked(commodity: Commodity) {
		console.log('Commodity ', commodity, ' Selected');
		this.selectedCommodity = commodity;
		this.onCommoditySelectedEmitter.emit(commodity);
	}


	private searchCommoditiesTerms: Subject<string> = new Subject();

	ngOnInit() {
		this.commodities$ = this.searchCommoditiesTerms.pipe(
			startWith(this.searchQuery),
			debounceTime(500),
			distinctUntilChanged(),
			switchMap((query) => this.commodityService.searchCommodities(query)),
			share()
		);
		this.searchCommodity();
	}

	searchCommodity() {
		this.searchCommoditiesTerms.next(this.searchQuery);
	}

}



