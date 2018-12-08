import { TestBed } from '@angular/core/testing';

import { FreeDateService } from './free-date.service';

describe('FreeDateService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: FreeDateService = TestBed.get(FreeDateService);
    expect(service).toBeTruthy();
  });
});
