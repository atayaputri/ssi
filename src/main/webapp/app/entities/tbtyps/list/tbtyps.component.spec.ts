import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { TBTYPSService } from '../service/tbtyps.service';

import { TBTYPSComponent } from './tbtyps.component';

describe('TBTYPS Management Component', () => {
  let comp: TBTYPSComponent;
  let fixture: ComponentFixture<TBTYPSComponent>;
  let service: TBTYPSService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [TBTYPSComponent],
    })
      .overrideTemplate(TBTYPSComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(TBTYPSComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(TBTYPSService);

    const headers = new HttpHeaders();
    jest.spyOn(service, 'query').mockReturnValue(
      of(
        new HttpResponse({
          body: [{ tpscod: 'ABC' }],
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
    expect(comp.tBTYPS?.[0]).toEqual(expect.objectContaining({ tpscod: 'ABC' }));
  });
});
