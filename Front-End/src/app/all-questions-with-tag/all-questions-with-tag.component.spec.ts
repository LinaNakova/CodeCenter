import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AllQuestionsWithTagComponent } from './all-questions-with-tag.component';

describe('AllQuestionsWithTagComponent', () => {
  let component: AllQuestionsWithTagComponent;
  let fixture: ComponentFixture<AllQuestionsWithTagComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AllQuestionsWithTagComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AllQuestionsWithTagComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
