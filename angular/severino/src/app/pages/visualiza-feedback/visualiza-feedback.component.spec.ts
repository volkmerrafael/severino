import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { VisualizaFeedbackComponent } from './visualiza-feedback.component';

describe('VisualizaFeedbackComponent', () => {
  let component: VisualizaFeedbackComponent;
  let fixture: ComponentFixture<VisualizaFeedbackComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ VisualizaFeedbackComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(VisualizaFeedbackComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
