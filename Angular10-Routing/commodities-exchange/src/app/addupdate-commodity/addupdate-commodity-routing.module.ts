import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { AddupdateCommodityComponent } from './addupdate-commodity.component';

const routes: Routes = [{ path: '', component: AddupdateCommodityComponent }];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AddupdateCommodityRoutingModule { }
