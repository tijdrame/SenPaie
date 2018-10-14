/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SenPaieTestModule } from '../../../test.module';
import { SituationMatrimonialeComponent } from '../../../../../../main/webapp/app/entities/situation-matrimoniale/situation-matrimoniale.component';
import { SituationMatrimonialeService } from '../../../../../../main/webapp/app/entities/situation-matrimoniale/situation-matrimoniale.service';
import { SituationMatrimoniale } from '../../../../../../main/webapp/app/entities/situation-matrimoniale/situation-matrimoniale.model';

describe('Component Tests', () => {

    describe('SituationMatrimoniale Management Component', () => {
        let comp: SituationMatrimonialeComponent;
        let fixture: ComponentFixture<SituationMatrimonialeComponent>;
        let service: SituationMatrimonialeService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SenPaieTestModule],
                declarations: [SituationMatrimonialeComponent],
                providers: [
                    SituationMatrimonialeService
                ]
            })
            .overrideTemplate(SituationMatrimonialeComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(SituationMatrimonialeComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SituationMatrimonialeService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new SituationMatrimoniale(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.situationMatrimoniales[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
