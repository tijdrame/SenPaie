/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { SenPaieTestModule } from '../../../test.module';
import { StatutDetailComponent } from '../../../../../../main/webapp/app/entities/statut/statut-detail.component';
import { StatutService } from '../../../../../../main/webapp/app/entities/statut/statut.service';
import { Statut } from '../../../../../../main/webapp/app/entities/statut/statut.model';

describe('Component Tests', () => {

    describe('Statut Management Detail Component', () => {
        let comp: StatutDetailComponent;
        let fixture: ComponentFixture<StatutDetailComponent>;
        let service: StatutService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SenPaieTestModule],
                declarations: [StatutDetailComponent],
                providers: [
                    StatutService
                ]
            })
            .overrideTemplate(StatutDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(StatutDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(StatutService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new Statut(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.statut).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
