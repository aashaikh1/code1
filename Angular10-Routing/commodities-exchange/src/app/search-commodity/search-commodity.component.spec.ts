import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SearchCommodityComponent } from './search-commodity.component';

describe('SearchCommodityComponent', () => {
  let component: SearchCommodityComponent;
  let fixture: ComponentFixture<SearchCommodityComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SearchCommodityComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SearchCommodityComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
