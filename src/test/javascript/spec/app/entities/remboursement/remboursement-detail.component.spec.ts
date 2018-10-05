/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { SenPaieTestModule } from '../../../test.module';
import { RemboursementDetailComponent } from '../../../../../../main/webapp/app/entities/remboursement/remboursement-detail.component';
import { RemboursementService } from '../../../../../../main/webapp/app/entities/remboursement/remboursement.service';
import { Remboursement } from '../../../../../../main/webapp/app/entities/remboursement/remboursement.model';

describe('Component Tests', () => {

    describe('Remboursement Management Detail Component', () => {
        let comp: RemboursementDetailComponent;
        let fixture: ComponentFixture<RemboursementDetailComponent>;
        let service: RemboursementService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SenPaieTestModule],
                declarations: [RemboursementDetailComponent],
                providers: [
                    RemboursementService
                ]
            })
            .overrideTemplate(RemboursementDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(RemboursementDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RemboursementService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new Remboursement(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.remboursement).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
