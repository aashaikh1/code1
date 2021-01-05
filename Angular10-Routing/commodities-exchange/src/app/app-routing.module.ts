import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

const routes: Routes = [{ path: 'search-commodity', loadChildren: () => import('./search-commodity/search-commodity.module').then(m => m.SearchCommodityModule) }, { path: 'addupdate-commodity', loadChildren: () => import('./addupdate-commodity/addupdate-commodity.module').then(m => m.AddupdateCommodityModule) }];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
