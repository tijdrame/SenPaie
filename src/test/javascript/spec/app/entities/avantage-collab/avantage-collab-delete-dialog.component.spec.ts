/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { SenPaieTestModule } from '../../../test.module';
import { AvantageCollabDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/avantage-collab/avantage-collab-delete-dialog.component';
import { AvantageCollabService } from '../../../../../../main/webapp/app/entities/avantage-collab/avantage-collab.service';

describe('Component Tests', () => {

    describe('AvantageCollab Management Delete Component', () => {
        let comp: AvantageCollabDeleteDialogComponent;
        let fixture: ComponentFixture<AvantageCollabDeleteDialogComponent>;
        let service: AvantageCollabService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SenPaieTestModule],
                declarations: [AvantageCollabDeleteDialogComponent],
                providers: [
                    AvantageCollabService
                ]
            })
            .overrideTemplate(AvantageCollabDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(AvantageCollabDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AvantageCollabService);
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
