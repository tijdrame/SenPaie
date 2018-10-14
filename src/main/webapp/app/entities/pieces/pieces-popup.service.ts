import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { Pieces } from './pieces.model';
import { PiecesService } from './pieces.service';

@Injectable()
export class PiecesPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private piecesService: PiecesService

    ) {
        this.ngbModalRef = null;
    }

    open(component: Component, id?: number | any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }

            if (id) {
                this.piecesService.find(id)
                    .subscribe((piecesResponse: HttpResponse<Pieces>) => {
                        const pieces: Pieces = piecesResponse.body;
                        if (pieces.dateDebut) {
                            pieces.dateDebut = {
                                year: pieces.dateDebut.getFullYear(),
                                month: pieces.dateDebut.getMonth() + 1,
                                day: pieces.dateDebut.getDate()
                            };
                        }
                        if (pieces.dateExpiration) {
                            pieces.dateExpiration = {
                                year: pieces.dateExpiration.getFullYear(),
                                month: pieces.dateExpiration.getMonth() + 1,
                                day: pieces.dateExpiration.getDate()
                            };
                        }
                        if (pieces.dateCreated) {
                            pieces.dateCreated = {
                                year: pieces.dateCreated.getFullYear(),
                                month: pieces.dateCreated.getMonth() + 1,
                                day: pieces.dateCreated.getDate()
                            };
                        }
                        if (pieces.dateDeleted) {
                            pieces.dateDeleted = {
                                year: pieces.dateDeleted.getFullYear(),
                                month: pieces.dateDeleted.getMonth() + 1,
                                day: pieces.dateDeleted.getDate()
                            };
                        }
                        if (pieces.dateUpdated) {
                            pieces.dateUpdated = {
                                year: pieces.dateUpdated.getFullYear(),
                                month: pieces.dateUpdated.getMonth() + 1,
                                day: pieces.dateUpdated.getDate()
                            };
                        }
                        this.ngbModalRef = this.piecesModalRef(component, pieces);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.piecesModalRef(component, new Pieces());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    piecesModalRef(component: Component, pieces: Pieces): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.pieces = pieces;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        });
        return modalRef;
    }
}
