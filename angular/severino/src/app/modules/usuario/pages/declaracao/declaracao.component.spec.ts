import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DeclaracaoComponent } from './declaracao.component';

describe('DeclaracaoComponent', () => {
  let component: DeclaracaoComponent;
  let fixture: ComponentFixture<DeclaracaoComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DeclaracaoComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DeclaracaoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
