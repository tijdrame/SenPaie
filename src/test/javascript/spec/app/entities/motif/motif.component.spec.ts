/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SenPaieTestModule } from '../../../test.module';
import { MotifComponent } from '../../../../../../main/webapp/app/entities/motif/motif.component';
import { MotifService } from '../../../../../../main/webapp/app/entities/motif/motif.service';
import { Motif } from '../../../../../../main/webapp/app/entities/motif/motif.model';

describe('Component Tests', () => {

    describe('Motif Management Component', () => {
        let comp: MotifComponent;
        let fixture: ComponentFixture<MotifComponent>;
        let service: MotifService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SenPaieTestModule],
                declarations: [MotifComponent],
                providers: [
                    MotifService
                ]
            })
            .overrideTemplate(MotifComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(MotifComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MotifService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Motif(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.motifs[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
