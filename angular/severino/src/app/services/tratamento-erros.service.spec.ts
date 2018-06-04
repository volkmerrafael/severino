import { TestBed, inject } from '@angular/core/testing';

import { TratamentoErrosService } from './tratamento-erros.service';

describe('TratamentoErrosService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [TratamentoErrosService]
    });
  });

  it('should be created', inject([TratamentoErrosService], (service: TratamentoErrosService) => {
    expect(service).toBeTruthy();
  }));
});
