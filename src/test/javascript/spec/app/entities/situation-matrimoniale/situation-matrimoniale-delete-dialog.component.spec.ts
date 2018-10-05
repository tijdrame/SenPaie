/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { SenPaieTestModule } from '../../../test.module';
import { SituationMatrimonialeDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/situation-matrimoniale/situation-matrimoniale-delete-dialog.component';
import { SituationMatrimonialeService } from '../../../../../../main/webapp/app/entities/situation-matrimoniale/situation-matrimoniale.service';

describe('Component Tests', () => {

    describe('SituationMatrimoniale Management Delete Component', () => {
        let comp: SituationMatrimonialeDeleteDialogComponent;
        let fixture: ComponentFixture<SituationMatrimonialeDeleteDialogComponent>;
        let service: SituationMatrimonialeService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SenPaieTestModule],
                declarations: [SituationMatrimonialeDeleteDialogComponent],
                providers: [
                    SituationMatrimonialeService
                ]
            })
            .overrideTemplate(SituationMatrimonialeDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(SituationMatrimonialeDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SituationMatrimonialeService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        spyOn(service, 'delete').and.returnValue(Observable.of({}));

                        // WHEN
                        comp.confirmDelete(123);
                        tick();

                        // THEN
                        expect(service.delete).toHaveBeenCalledWith(123);
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
