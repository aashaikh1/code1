import { Component } from '@angular/core';

import { CommodityService } from './commodity-core/services/commodity.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'Commodities Exchange';
}
