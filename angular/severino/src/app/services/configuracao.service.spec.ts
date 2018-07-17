import { TestBed, inject } from '@angular/core/testing';

import { ConfiguracaoService } from './configuracao.service';

describe('ConfiguracaoService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [ConfiguracaoService]
    });
  });

  it('should be created', inject([ConfiguracaoService], (service: ConfiguracaoService) => {
    expect(service).toBeTruthy();
  }));
});
