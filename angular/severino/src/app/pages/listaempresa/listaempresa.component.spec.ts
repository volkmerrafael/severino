import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ListaEmpresaComponent } from './listaempresa.component';

describe('ListaEmpresaComponent', () => {
  let component: ListaEmpresaComponent;
  let fixture: ComponentFixture<ListaEmpresaComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ListaEmpresaComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ListaEmpresaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
