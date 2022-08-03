import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TagForSearchComponent } from './tag-for-search.component';

describe('TagForSearchComponent', () => {
  let component: TagForSearchComponent;
  let fixture: ComponentFixture<TagForSearchComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TagForSearchComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TagForSearchComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
