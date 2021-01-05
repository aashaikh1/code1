import { HttpClient, HttpHeaders, HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { BehaviorSubject } from 'rxjs/BehaviorSubject';
import { Subject } from 'rxjs/Subject';

import { Component, OnInit, Injectable } from '@angular/core';
import { throwError } from "rxjs";
import { catchError, map } from "rxjs/operators";

import { Commodity } from '../model/commodity';
import { CommodityHistory } from '../model/commodity-history';

import { Observable } from 'rxjs/Observable';
import { _throw as ObservableThrow } from 'rxjs/observable/throw';
import { of as ObservableOf } from 'rxjs/observable/of';

@Injectable({
  providedIn: 'root'
})
export class CommodityService implements OnInit{
	private commoditiesServerPort = 3000;
	public commodities$: Subject<Number> = new BehaviorSubject<Number>(0);

	public notifyCommoditiesObservers(){
		let now = Date.now();
		console.log(now);
		this.commodities$.next(now);
	}

	ngOnInit() {
		this.getCommodities();
	}  
	
	httpOptions1 = {
	  headers: new HttpHeaders({ "Content-Type": "application/json" })
	};


	httpOptions2 = {
	  headers: new HttpHeaders({ "Content-Type": "application/json", "Access-Control-Allow-Origin": "http://localhost:4200", "Vary": "Origin", "Access-Control-Allow-Methods": "GET,PUT,POST,PATCH,OPTIONS", "Access-Control-Allow-Headers": "Access-Control-Allow-Origin, Access-Control-Allow-Methods, Content-Type, Accept, Accept-Language, Origin, User-Agent" })
	};
	
  constructor(private http: HttpClient) {}

  saveCommodity(commodity: Commodity): Observable<any> {
	let res: Observable<any>;
	res = this.http.post<any>('http://localhost:3000/api/commodity', commodity, {
      headers: new HttpHeaders()
		  .set('Content-Type', 'application/json')
    });
	this.notifyCommoditiesObservers();
	return res;
  }

  deleteCommodityInService(commodity: Commodity): Observable<any> {
	let res: Observable<any>;
	res = this.http.post<any>('http://localhost:3000/api/commoditydelete', commodity, {
      headers: new HttpHeaders()
		  .set('Content-Type', 'application/json')
    });
	this.notifyCommoditiesObservers();
	return res;
  }

  togglePreferredInService(commodity: Commodity): Observable<any> {
	let res: Observable<any>;  
	res = this.http.post<any>('http://localhost:3000/api/commoditypreferred', commodity, {
      headers: new HttpHeaders()
		  .set('Content-Type', 'application/json')
    });
	
	this.notifyCommoditiesObservers();
	return res;	
  }  
  
  getCommodities() : Observable<Commodity[]> {
	let res: Observable<Commodity[]>;  
    res = this.http.get<Commodity[]>('http://localhost:3000/api/commodity');
	return res;
  }
  

  searchCommodities(searchquery: string) : Observable<Commodity[]> {
	let res: Observable<Commodity[]>;  
    res = this.http.get<Commodity[]>(`http://localhost:3000/api/searchcommodity?q=${searchquery}`);
	return res;
  }

  getCommoditiesHistory(searchquery: string) : Observable<CommodityHistory> {
	let res: Observable<CommodityHistory>;  
    res = this.http.get<CommodityHistory>(`http://localhost:3000/api/commoditypricehistory?q=${searchquery}`);
	return res;
  }
  
}


