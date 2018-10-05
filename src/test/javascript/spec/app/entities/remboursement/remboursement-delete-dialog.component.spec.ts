/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { SenPaieTestModule } from '../../../test.module';
import { RemboursementDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/remboursement/remboursement-delete-dialog.component';
import { RemboursementService } from '../../../../../../main/webapp/app/entities/remboursement/remboursement.service';

describe('Component Tests', () => {

    describe('Remboursement Management Delete Component', () => {
        let comp: RemboursementDeleteDialogComponent;
        let fixture: ComponentFixture<RemboursementDeleteDialogComponent>;
        let service: RemboursementService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SenPaieTestModule],
                declarations: [RemboursementDeleteDialogComponent],
                providers: [
                    RemboursementService
                ]
            })
            .overrideTemplate(RemboursementDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(RemboursementDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RemboursementService);
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
