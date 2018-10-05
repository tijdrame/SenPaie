/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { SenPaieTestModule } from '../../../test.module';
import { FonctionDetailComponent } from '../../../../../../main/webapp/app/entities/fonction/fonction-detail.component';
import { FonctionService } from '../../../../../../main/webapp/app/entities/fonction/fonction.service';
import { Fonction } from '../../../../../../main/webapp/app/entities/fonction/fonction.model';

describe('Component Tests', () => {

    describe('Fonction Management Detail Component', () => {
        let comp: FonctionDetailComponent;
        let fixture: ComponentFixture<FonctionDetailComponent>;
        let service: FonctionService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SenPaieTestModule],
                declarations: [FonctionDetailComponent],
                providers: [
                    FonctionService
                ]
            })
            .overrideTemplate(FonctionDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(FonctionDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(FonctionService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new Fonction(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.fonction).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
