import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CommodityObjectComponent } from './commodity-object.component';

describe('CommodityObjectComponent', () => {
  let component: CommodityObjectComponent;
  let fixture: ComponentFixture<CommodityObjectComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CommodityObjectComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CommodityObjectComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
