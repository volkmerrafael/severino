import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PontoComponent } from './ponto.component';

describe('PontoComponent', () => {
  let component: PontoComponent;
  let fixture: ComponentFixture<PontoComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PontoComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PontoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
