import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';

import { CommodityCoreModule } from './commodity-core/commodity-core.module';

@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    CommodityCoreModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }


