import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { MFHDRService } from '../service/mfhdr.service';

import { MFHDRComponent } from './mfhdr.component';

describe('MFHDR Management Component', () => {
  let comp: MFHDRComponent;
  let fixture: ComponentFixture<MFHDRComponent>;
  let service: MFHDRService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [MFHDRComponent],
    })
      .overrideTemplate(MFHDRComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(MFHDRComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(MFHDRService);

    const headers = new HttpHeaders();
    jest.spyOn(service, 'query').mockReturnValue(
      of(
        new HttpResponse({
          body: [{ hdno: 123 }],
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
    expect(comp.mFHDRS?.[0]).toEqual(expect.objectContaining({ hdno: 123 }));
  });
});
