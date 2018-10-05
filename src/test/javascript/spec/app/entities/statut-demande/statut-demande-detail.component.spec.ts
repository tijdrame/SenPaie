/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { SenPaieTestModule } from '../../../test.module';
import { StatutDemandeDetailComponent } from '../../../../../../main/webapp/app/entities/statut-demande/statut-demande-detail.component';
import { StatutDemandeService } from '../../../../../../main/webapp/app/entities/statut-demande/statut-demande.service';
import { StatutDemande } from '../../../../../../main/webapp/app/entities/statut-demande/statut-demande.model';

describe('Component Tests', () => {

    describe('StatutDemande Management Detail Component', () => {
        let comp: StatutDemandeDetailComponent;
        let fixture: ComponentFixture<StatutDemandeDetailComponent>;
        let service: StatutDemandeService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SenPaieTestModule],
                declarations: [StatutDemandeDetailComponent],
                providers: [
                    StatutDemandeService
                ]
            })
            .overrideTemplate(StatutDemandeDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(StatutDemandeDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(StatutDemandeService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new StatutDemande(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.statutDemande).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
