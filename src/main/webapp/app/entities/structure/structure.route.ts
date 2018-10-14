import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { StructureComponent } from './structure.component';
import { StructureDetailComponent } from './structure-detail.component';
import { StructurePopupComponent } from './structure-dialog.component';
import { StructureDeletePopupComponent } from './structure-delete-dialog.component';

@Injectable()
export class StructureResolvePagingParams implements Resolve<any> {

    constructor(private paginationUtil: JhiPaginationUtil) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const page = route.queryParams['page'] ? route.queryParams['page'] : '1';
        const sort = route.queryParams['sort'] ? route.queryParams['sort'] : 'id,asc';
        return {
            page: this.paginationUtil.parsePage(page),
            predicate: this.paginationUtil.parsePredicate(sort),
            ascending: this.paginationUtil.parseAscending(sort)
      };
    }
}

export const structureRoute: Routes = [
    {
        path: 'structure',
        component: StructureComponent,
        resolve: {
            'pagingParams': StructureResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'senPaieApp.structure.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'structure/:id',
        component: StructureDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'senPaieApp.structure.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const structurePopupRoute: Routes = [
    {
        path: 'structure-new',
        component: StructurePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'senPaieApp.structure.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'structure/:id/edit',
        component: StructurePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'senPaieApp.structure.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'structure/:id/delete',
        component: StructureDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'senPaieApp.structure.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
