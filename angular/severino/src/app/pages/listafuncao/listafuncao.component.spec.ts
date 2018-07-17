import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ListaFuncaoComponent } from './listafuncao.component';

describe('ListaFuncaoComponent', () => {
  let component: ListaFuncaoComponent;
  let fixture: ComponentFixture<ListaFuncaoComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ListaFuncaoComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ListaFuncaoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
