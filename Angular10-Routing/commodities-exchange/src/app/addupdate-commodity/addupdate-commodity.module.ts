import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule }   from '@angular/forms';
import { CommodityObjectComponent } from './commodity/commodity-object/commodity-object.component';
import { CreateCommodityComponent } from './commodity/create-commodity/create-commodity.component';
import { CommodityListComponent } from './commodity/commodity-list/commodity-list.component';
import { HttpClientModule } from '@angular/common/http';

import { AddupdateCommodityRoutingModule } from './addupdate-commodity-routing.module';
import { AddupdateCommodityComponent } from './addupdate-commodity.component';


@NgModule({
  declarations: [
    AddupdateCommodityComponent,
    CommodityObjectComponent,
    CreateCommodityComponent,
    CommodityListComponent
  ],
  imports: [
	CommonModule,
	AddupdateCommodityRoutingModule,
	ReactiveFormsModule,
	HttpClientModule 
  ],
})
export class AddupdateCommodityModule { }
