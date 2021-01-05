import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AddupdateCommodityComponent } from './addupdate-commodity.component';

describe('AddupdateCommodityComponent', () => {
  let component: AddupdateCommodityComponent;
  let fixture: ComponentFixture<AddupdateCommodityComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AddupdateCommodityComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AddupdateCommodityComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
