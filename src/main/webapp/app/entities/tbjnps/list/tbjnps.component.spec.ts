import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { TBJNPSService } from '../service/tbjnps.service';

import { TBJNPSComponent } from './tbjnps.component';

describe('TBJNPS Management Component', () => {
  let comp: TBJNPSComponent;
  let fixture: ComponentFixture<TBJNPSComponent>;
  let service: TBJNPSService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [TBJNPSComponent],
    })
      .overrideTemplate(TBJNPSComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(TBJNPSComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(TBJNPSService);

    const headers = new HttpHeaders();
    jest.spyOn(service, 'query').mockReturnValue(
      of(
        new HttpResponse({
          body: [{ jpscod: 'ABC' }],
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
    expect(comp.tBJNPS?.[0]).toEqual(expect.objectContaining({ jpscod: 'ABC' }));
  });
});
