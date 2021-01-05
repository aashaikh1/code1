import { Component, Input } from '@angular/core';
import { Commodity } from '../../../commodity-core/model/commodity';

@Component({
  selector: 'app-search-commodity-object',
  templateUrl: './search-commodity-object.component.html',
  styleUrls: ['./search-commodity-object.component.css']
})
export class SearchCommodityObjectComponent {
  @Input() public commodity: Commodity;

  constructor() { }

  isUpwardChange(comm:Commodity): boolean {
    return comm.currentValue >= comm.previousValue;
  }   
}
