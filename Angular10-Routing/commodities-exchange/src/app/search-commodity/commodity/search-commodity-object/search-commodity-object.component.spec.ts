import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SearchCommodityObjectComponent } from './search-commodity-object.component';

describe('SearchCommodityObjectComponent', () => {
  let component: SearchCommodityObjectComponent;
  let fixture: ComponentFixture<SearchCommodityObjectComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SearchCommodityObjectComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SearchCommodityObjectComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
