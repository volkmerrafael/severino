import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EditarTipoEventoComponent } from './editar-tipo-evento.component';

describe('EditarTipoEventoComponent', () => {
  let component: EditarTipoEventoComponent;
  let fixture: ComponentFixture<EditarTipoEventoComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EditarTipoEventoComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EditarTipoEventoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
