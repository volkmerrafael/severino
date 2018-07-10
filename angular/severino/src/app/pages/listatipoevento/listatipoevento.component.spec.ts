import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ListaTipoEventoComponent } from './listatipoevento.component';

describe('ListaTipoEventoComponent', () => {
  let component: ListaTipoEventoComponent;
  let fixture: ComponentFixture<ListaTipoEventoComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ListaTipoEventoComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ListaTipoEventoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
