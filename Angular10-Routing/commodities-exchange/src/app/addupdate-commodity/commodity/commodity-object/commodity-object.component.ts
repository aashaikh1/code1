import { Component, OnInit, Input, Output, EventEmitter  } from '@angular/core';
import { Commodity } from '../../../commodity-core/model/commodity';

@Component({
  selector: 'app-commodity-object',
  templateUrl: './commodity-object.component.html',
  styleUrls: ['./commodity-object.component.css']
})
export class CommodityObjectComponent {
  @Input() public commodity: Commodity;
  @Output() togglePreferredEmmiter: EventEmitter<Commodity>;
  @Output() deleteEmmiter: EventEmitter<Commodity>;

  constructor() { 
    this.togglePreferredEmmiter = new EventEmitter<Commodity>();
	this.deleteEmmiter = new EventEmitter<Commodity>();
  }

  togglePreferred(event) {
    console.log('Changing the preferred state for this commodity', event);
	this.togglePreferredEmmiter.emit(this.commodity);
  }


  deleteClicked(event) {
    console.log('Delete commodity', event);
	this.deleteEmmiter.emit(this.commodity);
  }
  
  isUpwardChange(comm:Commodity): boolean {
    return comm.currentValue >= comm.previousValue;
  }   
}
