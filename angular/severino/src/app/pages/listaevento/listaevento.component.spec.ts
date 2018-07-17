import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ListaEventoComponent } from './listaevento.component';

describe('ListaEventoComponent', () => {
  let component: ListaEventoComponent;
  let fixture: ComponentFixture<ListaEventoComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ListaEventoComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ListaEventoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
