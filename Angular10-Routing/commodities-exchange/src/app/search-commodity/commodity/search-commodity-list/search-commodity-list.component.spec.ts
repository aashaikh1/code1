import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SearchCommodityListComponent } from './search-commodity-list.component';

describe('SearchCommodityListComponent', () => {
  let component: SearchCommodityListComponent;
  let fixture: ComponentFixture<SearchCommodityListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SearchCommodityListComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SearchCommodityListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
