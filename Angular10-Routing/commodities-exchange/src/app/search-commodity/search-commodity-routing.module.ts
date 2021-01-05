import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { SearchCommodityComponent } from './search-commodity.component';

const routes: Routes = [{ path: '', component: SearchCommodityComponent }];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class SearchCommodityRoutingModule { }
