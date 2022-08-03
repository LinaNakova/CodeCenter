import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SmallAnswerCardComponent } from './small-answer-card.component';

describe('SmallAnswerCardComponent', () => {
  let component: SmallAnswerCardComponent;
  let fixture: ComponentFixture<SmallAnswerCardComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SmallAnswerCardComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SmallAnswerCardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
