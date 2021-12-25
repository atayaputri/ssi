jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IMFSHM, MFSHM } from '../mfshm.model';
import { MFSHMService } from '../service/mfshm.service';

import { MFSHMRoutingResolveService } from './mfshm-routing-resolve.service';

describe('MFSHM routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: MFSHMRoutingResolveService;
  let service: MFSHMService;
  let resultMFSHM: IMFSHM | undefined;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [Router, ActivatedRouteSnapshot],
    });
    mockRouter = TestBed.inject(Router);
    mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
    routingResolveService = TestBed.inject(MFSHMRoutingResolveService);
    service = TestBed.inject(MFSHMService);
    resultMFSHM = undefined;
  });

  describe('resolve', () => {
    it('should return IMFSHM returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultMFSHM = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultMFSHM).toEqual({ id: 123 });
    });

    it('should return new IMFSHM if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultMFSHM = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultMFSHM).toEqual(new MFSHM());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as MFSHM })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultMFSHM = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultMFSHM).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
