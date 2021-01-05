
import { NgModule,NO_ERRORS_SCHEMA,CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { SearchCommodityObjectComponent } from './commodity/search-commodity-object/search-commodity-object.component';
import { SearchCommodityListComponent } from './commodity/search-commodity-list/search-commodity-list.component';
import { HttpClientModule } from '@angular/common/http';

import { SearchCommodityRoutingModule } from './search-commodity-routing.module';
import { SearchCommodityComponent } from './search-commodity.component';

import * as PlotlyJS from 'plotly.js/dist/plotly.js';
import { PlotlyModule } from 'angular-plotly.js';

PlotlyModule.plotlyjs = PlotlyJS;


import { CommodityPlotComponent } from './commodity/commodity-plot/commodity-plot.component';

@NgModule({
  declarations: [
    SearchCommodityComponent,
    SearchCommodityObjectComponent,
    SearchCommodityListComponent,
	CommodityPlotComponent
  ],
  imports: [
	CommonModule,
	SearchCommodityRoutingModule,
	FormsModule,
	HttpClientModule,
	PlotlyModule
  ],
  schemas: [
    CUSTOM_ELEMENTS_SCHEMA,
    NO_ERRORS_SCHEMA
  ]  
})
export class SearchCommodityModule { }
