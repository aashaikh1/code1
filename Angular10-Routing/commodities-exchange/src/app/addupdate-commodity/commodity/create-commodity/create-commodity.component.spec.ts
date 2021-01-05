import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateCommodityComponent } from './create-commodity.component';

describe('CreateCommodityComponent', () => {
  let component: CreateCommodityComponent;
  let fixture: ComponentFixture<CreateCommodityComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CreateCommodityComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CreateCommodityComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
