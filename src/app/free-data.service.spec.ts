import { TestBed } from '@angular/core/testing';

import { FreeDataService } from './free-data.service';

describe('FreeDataService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: FreeDataService = TestBed.get(FreeDataService);
    expect(service).toBeTruthy();
  });
});
