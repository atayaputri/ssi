import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { TBCOMSService } from '../service/tbcoms.service';

import { TBCOMSComponent } from './tbcoms.component';

describe('TBCOMS Management Component', () => {
  let comp: TBCOMSComponent;
  let fixture: ComponentFixture<TBCOMSComponent>;
  let service: TBCOMSService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [TBCOMSComponent],
    })
      .overrideTemplate(TBCOMSComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(TBCOMSComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(TBCOMSService);

    const headers = new HttpHeaders();
    jest.spyOn(service, 'query').mockReturnValue(
      of(
        new HttpResponse({
          body: [{ cocode: 'ABC' }],
          headers,
        })
      )
    );
  });

  it('Should call load all on init', () => {
    // WHEN
    comp.ngOnInit();

    // THEN
    expect(service.query).toHaveBeenCalled();
    expect(comp.tBCOMS?.[0]).toEqual(expect.objectContaining({ cocode: 'ABC' }));
  });
});
