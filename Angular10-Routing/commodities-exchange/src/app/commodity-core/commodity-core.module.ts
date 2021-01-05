import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { CommodityService } from './services/commodity.service';
import { HttpClientModule } from '@angular/common/http';

@NgModule({
  imports: [
    CommonModule,
	HttpClientModule
  ],
  providers: [
	CommodityService
  ]  
})
export class CommodityCoreModule { }

  